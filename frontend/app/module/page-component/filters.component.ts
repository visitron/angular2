import {Component, OnDestroy} from "@angular/core";
import {Location} from "@angular/common";
import {Router, NavigationEnd} from "@angular/router";
import {DataProvider} from "../service/data.service";
import "rxjs/operator/map";
import {Subscription} from "rxjs";
import {SlickGridProvider, FilterGroup, Filter, FilterConfig, FilterGroupBuilder} from "../service/slick-grid.service";

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

        this.subscription = router.events.subscribe(event => {
            if (event instanceof NavigationEnd) {
                getFilters(event.urlAfterRedirects);
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