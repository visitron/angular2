import {Component, OnInit} from '@angular/core'


@Component({
    selector: 'my-app',
    templateUrl: 'pages/service-component.html'
})

export class AppComponent implements OnInit {
    progress: number = 100;
    severity: string = 'progress-bar-success';
    showWarning: boolean;

    constructor() {

    }

    ngOnInit(): void {
    }
}