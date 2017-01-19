import {Component, OnInit, OnDestroy} from "@angular/core";

@Component({
    selector: 'slick-grid',
    templateUrl: 'mockup/parts/slick-grid.html'
})
export class SlickGridComponent implements OnInit, OnDestroy {

    private grid: Slick.Grid<any> = null;

    constructor() {
        console.log('SlickGridComponent');
    }

    ngOnInit(): void {
        console.log('subscribed on SlickGridUpdater');
    }

    ngOnDestroy(): void {
    }

}