import {Injectable} from "@angular/core";
import "rxjs/add/operator/map";
import {DataProvider} from "./data.service";
import {Observable} from "rxjs/Observable";
import {Observer} from "rxjs/Observer";

@Injectable()
export class SlickGridProvider {

    private grid: Slick.Grid<any>;
    private view: Slick.Data.DataView<any>;
    private dataProvider: DataProvider = null;
    private location: string = null;
    private publisher: Observable<string>;
    private observer: Observer<string>;
    private activatedFilterGroups: FilterGroup[] = null;

    constructor() {
        this.publisher = new Observable<string>((observer: Observer<string>) => {
            this.observer = observer;
            return () => {};
        });

    }

    private extractColumnDescriptors(data: any[]): any[] {
        if (_.isEmpty(data)) return [];

        let columnIds: string[] = _.keys(data[0]);
        let result: {id: string, name: string, field: string, sortable: boolean, formatter?: any}[] = [];

        columnIds.forEach(id => {
            let name: string = id[0].toUpperCase();
            for (let i = 1; i < id.length; i++) {
                if (id[i].match(/[A-Z]/)) {
                    name += ' ' + id[i].toUpperCase();
                } else {
                    name += id[i];
                }
            }

            //todo add formatters for highlighting search matches
            result.push({id: id, name: name, field: id, sortable: true});
        });

        return result;
    }

    public onLoad(callback: (state: string) => void) {
        return this.publisher.subscribe(callback);
    }

    public getItems(): any[] {
        return this.view.getItems();
    }

    public attachDataProvider(dataProvider: DataProvider, location: string) {
        this.dataProvider = dataProvider;
        this.location = location;
    }

    public refresh(): void {
        this.dataProvider.getData(this.location, (data: any[]) => {
            this.view.beginUpdate();
            this.view.setItems(data);
            this.view.endUpdate();
            this.grid.invalidate();
            this.grid.onSort.notify(<any>{sortAsc: true, sortCol: {field: 'id'}});
            this.observer.next("SG has been reloaded");
        });
    }

    public create(data: any[], columnNameOverrides?: {field: string, name: string}[]): Slick.Grid<any> {
        this.dataProvider = null;
        this.location = null;
        if (_.isEmpty(data)) return null;
        let columns = this.extractColumnDescriptors(data);
        let options = {
            enableCellNavigation: true,
            enableColumnReorder: false,
            forceFitColumns: true
        };

        let getItem = function (index: number) {
            return data[index];
        };

        let getLength = function (): number {return data.length};

        let view: Slick.Data.DataView<any> = new Slick.Data.DataView<any>();
        let grid = new Slick.Grid("#grid-id", view, columns, options);

        view.onRowCountChanged.subscribe((e, args) => {
            grid.updateRowCount();
            grid.render();
        });

        view.onRowsChanged.subscribe((e, args) => {
            grid.invalidateRows(args.rows);
            grid.render();
        });

        view.setFilter((item: any, groups0: any[]) => {
            if (_.isEmpty(groups0)) return true;

            let search0: (item0: any, search: Search) => boolean = (item0: any, search: Search) => {
                let like = function (value: any, pattern: any): boolean {
                    if (typeof value == 'string') {
                        return value.toLowerCase().indexOf(pattern) !== -1;
                    } else if (typeof value == 'number') {
                        return value.toString().indexOf(pattern) !== -1;
                    }
                    return false;
                };

                let result0: boolean = false;
                search.fields.forEach(field => {
                    result0 = result0 || like(item0[field], search.value);
                });

                return result0;
            };

            let groups: FilterGroup[] = [];
            let search: Search = null;
            groups0.forEach(value => {
                if (value instanceof FilterGroup) {
                    groups.push(value);
                } else if (value instanceof Search) {
                    search = value;
                }
            });

            let result: boolean[] = [true];
            for (let i = 0; i < groups.length; i++) {
                for (let j = 0; j < groups[i].filters.length; j++) {
                    let filter = groups[i].filters[j];
                    if (filter.active) {
                        result[i] = result[i] && item[filter.field] == filter.value;
                        if (!result[i]) break;
                    }
                }
            }

            if (search !== null) result.push(search0(item, search));

            return result.reduce((b1, b2) => b1 && b2);
        });

        let comparator = function (e1: any, e2: any): number {
            let res: number = 0;
            let type: string = typeof e1;
            switch (type) {
                case 'string':
                    res = e1.localeCompare(e2);
                    break;
                case 'number':
                case 'boolean':
                    res = e1 - e2;
                    break;
            }

            return res;
        };


        grid.onSort.subscribe((event, args) => {

            let type: string = typeof data[0][args.sortCol.field];

            let comparator = function (e1: any, e2: any): number {
                let res: number = 0;
                switch (type) {
                    case 'string':
                        res = e1[args.sortCol.field].localeCompare(e2[args.sortCol.field]);
                        break;
                    case 'number':
                    case 'boolean':
                        res = e1[args.sortCol.field] - e2[args.sortCol.field];
                        break;
                }

                return res;
            };

            view.sort(comparator, args.sortAsc);
            grid.setSelectedRows([]);
        });

        grid.init();
        grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: true}));
        grid.setSortColumn("id", true);

        view.beginUpdate();
        view.setItems(data);
        view.endUpdate();

        grid.onSort.notify(<any>{sortAsc: true, sortCol: {field: 'id'}});
        view.setFilterArgs(null);

        this.grid = grid;
        this.view = view;

        this.observer.next("FG loaded");
        return grid;
    }

    public filter(filter: FilterGroup[]) {
        this.activatedFilterGroups = filter;
        this.view.setFilterArgs(filter);
        this.view.refresh();
    }

    public search(search: Search) {
        let filter: any[] = [];
        if (this.activatedFilterGroups !== null) filter.push(...this.activatedFilterGroups);
        filter.push(search);
        this.view.setFilterArgs(filter);
        this.view.refresh();
    }

    public getSelectedIds(): number[] {
        let rows: number[] = this.grid.getSelectedRows();
        return rows.map(row => this.grid.getDataItem(row)["id"]);
    }

    public select(mode: "all" | "nothing" | "invert"): void {
        let rowNums: number[] = [];
        let i: number = 0;
        switch (mode) {
            case "all":
                for (i = 0; i < this.grid.getDataLength(); i++) {
                    rowNums.push(i);
                }
                this.grid.setSelectedRows(rowNums);
                break;
            case "invert":
                for (i = 0; i < this.grid.getDataLength(); i++) {
                    rowNums.push(i);
                }
                let selectedRows: number[] = this.grid.getSelectedRows();
                selectedRows.forEach(id => {
                    rowNums.splice(rowNums.indexOf(id), 1);
                });
                this.grid.setSelectedRows(rowNums);
                break;
            case "nothing":
                this.grid.setSelectedRows([]);
                break;
        }
    }

}

export class Search {
    public fields: string[];
    public value: string | number;
}

type FilterType = 'boolean' | 'numeric' | 'date';

export class FilterConfig {
    name: string;
    type:  FilterType;
    field: string;
}

export class FilterGroupBuilder {
    static createGroup(config: FilterConfig, data: any[]): FilterGroup {
        let group: FilterGroup;
        switch (config.type) {
            case "date":
                group = new FilterGroup(config.name, [new Filter("date", config.field)]);
                break;
            case "numeric":
                group = new FilterGroup(config.name, [new Filter("numeric", config.field)]);
                break;
            case "boolean":
                group = new FilterGroup(config.name, this.createBooleanFilters(config, data));
                break;
        }

        return group;
    }

    private static createBooleanFilters(config: FilterConfig, data: any[]): Filter[] {
        return _
            .uniq(data, false, (value, index, data) => data[index][config.field])
            .map((value: any) => new Filter("boolean", config.field, value[config.field]));
    }

}

export class FilterGroup {
    constructor(private _name: string, private _filters: Filter[], private _expanded?: boolean) {
        this.expanded = _.isEmpty(_expanded) ? true : _expanded;
    }

    public get name(): string {return this._name}
    public get filters(): Filter[] {return this._filters}
    public get expanded(): boolean {return this._expanded}
    public set expanded(value: boolean) {this._expanded = value}
}

export class Filter {
    private _name: string;
    constructor(private _type: FilterType, private _field: string, private _value?: any, private _active?: boolean) {
        if (_type === 'boolean') {
            switch (this._value) {
                case true:
                    this._name = "Yes";
                    break;
                case false:
                    this._name = 'No';
                    break;
                default:
                    this._name = _value.substring(0, 1).toUpperCase() + _value.substring(1).toLowerCase();
            }
        }
    }

    public get name(): string {return this._name}
    public get type(): string {return this._type}
    public get active(): boolean {return this._active}
    public set active(value: boolean) {this._active = value}
    public get field(): string {return this._field};
    public set field(field: string) {this._field = field};
    public get value(): any {return this._value};
    public set value(value: any) {this._value = value};

    private _from: number | Date = null;
    private _to: number | Date = null;

    public get from(): number | Date {return this._from}
    public set from(value: number | Date) {this._from = value}

    public get to(): number | Date {return this._to}
    public set to(value: number | Date) {this._to = value}

    public get description(): string {
        if (this.type === 'boolean') {
            return `${this.name}`;
        }

        if (!_.isEmpty(this.from) && !_.isEmpty(this.to)) {
            if (this.from == this.to) {
                return `${this.name}: = ${this.from}`;
            }
            return `${this.name}: ${this.from} - ${this.to}`;
        }

        if (!_.isEmpty(this.from) && _.isEmpty(this.to)) {
            return `${this.name}: >= ${this.from}`;
        }

        return `${this.name}: <= ${this.to}`;
    }

}