import {Injectable} from "@angular/core";
import {Subscription} from "rxjs/Subscription";
import {Subject} from "rxjs/Subject";
import {Dialog} from "../page-component/dialog.component";

@Injectable()
export class DialogSupport {
    private subject: Subject<Dialog> = new Subject<Dialog>();

    public onOpen(callback: (dialog: Dialog) => void): Subscription {
        return this.subject.subscribe(callback);
    }

    public open(dialog: Dialog): void {
        this.subject.next(dialog);
    }
}