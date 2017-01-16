import {Component, OnInit} from "@angular/core";

@Component({
    // selector: 'homeManagement',
    // templateUrl: 'mockup/admin.html'
    templateUrl: 'mockup/admin.html'
})
export class AdminComponent implements OnInit {

    ngOnInit(): void {
        $('[data-toggle="tooltip"]').tooltip();
    }
}