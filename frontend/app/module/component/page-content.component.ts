import {Component, OnInit} from "@angular/core";

@Component({
    selector: 'page-content',
    templateUrl: 'mockup/content.html'
})
export class ContentComponent implements OnInit {

    ngOnInit(): void {

        debugger;
        let grid: Slick.Grid<any> = null;
        let columns = [
            {id: "title", name: "Title", field: "title"},
            {id: "duration", name: "Duration", field: "duration"},
            {id: "%", name: "% Complete", field: "percentComplete"},
            {id: "start", name: "Start", field: "start"},
            {id: "finish", name: "Finish", field: "finish"},
            {id: "effort-driven", name: "Effort Driven", field: "effortDriven"}
        ];
        let options = {
            enableCellNavigation: true,
            enableColumnReorder: false
        };
        let data: Array<any> = [];
        for (let i = 0; i < 500; i++) {
            data[i] = {
                title: "Task " + i,
                duration: "5 days",
                percentComplete: Math.round(Math.random() * 100),
                start: "01/01/2009",
                finish: "01/05/2009",
                effortDriven: (i % 5 == 0)
            };
        }
        grid = new Slick.Grid("#grid-id", data, columns, options);

    }
}