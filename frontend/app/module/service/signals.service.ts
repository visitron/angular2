import {Injectable} from "@angular/core";
import {Observable, Observer, Subscription} from "rxjs";
import {ActionContext} from "../page-component/actions.component";

@Injectable()
export class ActionService {
    private observable: Observable<ActionContext>;
    private observer: Observer<ActionContext>;

    constructor() {
        this.observable = Observable.create((observer: Observer<any>) => this.observer = observer);
    }

    sendAction(context: ActionContext): void {
        this.observer.next(context);
    }

    onActionSent(callback: (context: ActionContext) => void) : Subscription {
        return this.observable.subscribe(callback);
    }

}