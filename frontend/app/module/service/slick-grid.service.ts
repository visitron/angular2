import {Injectable} from "@angular/core";
import "rxjs/add/operator/map";

@Injectable()
export class SlickGridProvider {

    private extractColumnDescriptors(data: any[]): any[] {
        if (_.isEmpty(data)) return [];

        let columnIds: string[] = _.keys(data[0]);
        let result: {id: string, name: string, field: string}[] = [];

        columnIds.forEach(id => {
            let name: string = id[0].toUpperCase();
            for (let i = 1; i < id.length; i++) {
                if (id[i].match(/[A-Z]/)) {
                    name += ' ' + id[i].toUpperCase();
                } else {
                    name += id[i];
                }
            }

            result.push({id: id, name: name, field: id});
        });

        return result;
    }

    public create(data: any[], columnNameOverrides?: {field: string, name: string}[]): Slick.Grid<any> {
        let columns = this.extractColumnDescriptors(data);
        let options = {
            enableCellNavigation: true,
            enableColumnReorder: false,
            forceFitColumns: true,

        };
        return new Slick.Grid("#grid-id", data, columns, options);
    }

}