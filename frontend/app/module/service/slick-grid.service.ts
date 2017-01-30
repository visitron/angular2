import {Injectable} from "@angular/core";
import "rxjs/add/operator/map";
import {FilterGroup} from "../page-component/filters.component";
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

    constructor() {
        this.publisher = new Observable<string>((observer: Observer<string>) => {
            debugger;
            this.observer = observer;
            return () => {};
        });

    }

    private extractColumnDescriptors(data: any[]): any[] {
        if (_.isEmpty(data)) return [];

        let columnIds: string[] = _.keys(data[0]);
        let result: {id: string, name: string, field: string, sortable: boolean}[] = [];

        columnIds.forEach(id => {
            let name: string = id[0].toUpperCase();
            for (let i = 1; i < id.length; i++) {
                if (id[i].match(/[A-Z]/)) {
                    name += ' ' + id[i].toUpperCase();
                } else {
                    name += id[i];
                }
            }

            result.push({id: id, name: name, field: id, sortable: true});
        });

        return result;
    }

    public subscribe(callback: (state: string) => void) {
        return this.publisher.subscribe(callback);
    }

    public getData(): any[] {
        return this.grid.getData();
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
            this.grid.onSort.notify(<any>{sortAsc: true, sortCol: {field: 'id'}});
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

        view.setFilter((item: any, groups: FilterGroup[]) => {
            if (_.isEmpty(groups)) return true;

            let result: boolean[] = [];
            for (let i = 0; i < groups.length; i++) {
                result.push(true);
                for (let j = 0; j < groups[i].filters.length; j++) {
                    let filter = groups[i].filters[j];
                    if (filter.active) {
                        result[i] = result[i] && item[filter.field] == filter.value;
                        if (!result[i]) break;
                    }
                }
            }

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