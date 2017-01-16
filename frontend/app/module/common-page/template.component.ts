import {Component, OnInit} from "@angular/core";

@Component({
    // selector: 'homeManagement',
    // templateUrl: 'mockup/admin.html'
    templateUrl: 'mockup/parts/template.html'
})
export class TemplateComponent implements OnInit {

    ngOnInit(): void {
        $('[data-toggle="tooltip"]').tooltip();
    }
}