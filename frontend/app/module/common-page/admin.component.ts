import {Component, OnInit} from "@angular/core";

@Component({
    // selector: 'homeManagement',
    templateUrl: 'mockup/parts/template.html'
})
export class AdminComponent implements OnInit {

    ngOnInit(): void {
        $('[data-toggle="tooltip"]').tooltip();
    }
}