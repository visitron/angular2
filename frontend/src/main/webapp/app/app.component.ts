import {Component} from '@angular/core'
import {Observable} from "rxjs/Observable";
import 'rxjs/Rx'

@Component({
    selector: 'my-app',
    templateUrl: 'pages/service-component.html'
})

export class AppComponent {
    progress: number = 100;
    severity: string = 'progress-bar-success';
    showWarning: boolean;

    constructor() {
        let timeEmulator = Observable.timer(2000, 100).take(100);
        timeEmulator.subscribe(t => {
            this.progress -= 1;
            if (this.progress < 50 && this.progress >= 15) {
                this.severity = 'progress-bar-warning';
            } else if (this.progress < 15) {
                this.severity = 'progress-bar-danger';
            }
        }, null, () => {
            this.showWarning = true;
        });


    }
}