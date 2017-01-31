import {Component, OnDestroy} from "@angular/core";
import {Location} from "@angular/common";
import {Router, NavigationStart} from "@angular/router";
import {DataProvider} from "../service/data.service";
import "rxjs/operator/map";
import {Subscription} from "rxjs";
import {SlickGridProvider} from "../service/slick-grid.service";

@Component({
    selector: 'filters',
    templateUrl: 'mockup/parts/filters.html'
})
export class FiltersComponent implements OnDestroy {

    private show: boolean = true;
    private filterGroups: FilterGroup[] = [];
    private appliedFilters: Filter[] = [];
    private allFiltersExpanded: boolean = true;
    private subscription: Subscription = null;

    constructor(private dataProvider: DataProvider, private router: Router, private location: Location, private slickGridProvider: SlickGridProvider) {

        console.log("FiltersComponent is created");

        let getFilters = (location: string) => {
            dataProvider.getPart(location, 'filters', data => {
                if (_.isEmpty(data)) {
                    this.show = false;
                    return;
                }

                this.show = true;
                this.filterGroups.splice(0);

                this.slickGridProvider.onLoad((state: string) => {
                    this.filterGroups = (<FilterConfig[]> data).map(config => FilterGroupBuilder.createGroup(config, this.slickGridProvider.getItems()));
                })

            });
        };

        getFilters(location.path());

        this.subscription = router.events.subscribe(event => {
            if (event instanceof NavigationStart) {
                getFilters(event.url);
            }
        });
    }

    ngOnDestroy(): void {
        console.log("FiltersComponent is destroyed");
        this.subscription.unsubscribe();
    }

    hasAnyFilter(): boolean {
        return this.appliedFilters.length > 0;
    }

    applyFilter(filter: Filter): void {
        filter.active = true;
        this.appliedFilters.push(filter);
        this.slickGridProvider.filter(this.filterGroups);
    }

    applyBtnDisabled(filter: Filter): boolean {
        return _.isEmpty(filter.from) && _.isEmpty(filter.to);
    }

    removeFilter(filter: Filter): void {
        filter.active = false;
        filter.from = null;
        filter.to = null;
        this.appliedFilters.splice(this.appliedFilters.indexOf(filter), 1);
        this.slickGridProvider.filter(this.filterGroups);
    }

    clearFilters(): void {
        this.appliedFilters.forEach(filter => {
            filter.active = false;
            filter.from = null;
            filter.to = null;
        });
        this.appliedFilters.splice(0);
        this.slickGridProvider.filter(null);
    }

    collapseOrExpand(group: FilterGroup): void {
        group.expanded = !group.expanded;
        let allExpanded = false;
        this.filterGroups.forEach(group => {allExpanded = allExpanded || group.expanded});
        this.allFiltersExpanded = allExpanded;
    }

    collapseOrExpandAll(): void {
        this.allFiltersExpanded = !this.allFiltersExpanded;
        this.filterGroups.forEach(group => group.expanded = this.allFiltersExpanded)
    }

}

type FilterType = 'boolean' | 'numeric' | 'date';

class FilterConfig {
    name: string;
    type:  FilterType;
    field: string;
}

class FilterGroupBuilder {
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