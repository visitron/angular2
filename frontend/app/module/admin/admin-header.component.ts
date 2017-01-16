import {Component, OnInit} from "@angular/core";

@Component({
    // selector: 'page-header',
    templateUrl: 'mockup/admin-header.html'
})
export class AdminComponent implements OnInit {

    ngOnInit(): void {
        $('[data-toggle="tooltip"]').tooltip();
    }
}