import {Component, OnInit} from "@angular/core";

@Component({
    // selector: 'page-header',
    templateUrl: 'mockup/admin/config.html'
})
export class AdminConfigComponent implements OnInit {
    ngOnInit(): void {
        $('[data-toggle="tooltip"]').tooltip();
    }
}