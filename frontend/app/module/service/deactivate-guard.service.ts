import {CanDeactivate, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {AdminConfigComponent} from "../admin/admin-config.component";
import {Observable} from "rxjs/Observable";
import "rxjs/add/observable/of";
import {Injectable} from "@angular/core";
import {DialogSupport} from "./dialog.service";
import {Dialog, DialogAction} from "../page-component/dialog.component";
import {Observer} from "rxjs";

@Injectable()
export class DeactivateGuard implements CanDeactivate<AdminConfigComponent> {

    constructor(private dialogSupport: DialogSupport) {}

    canDeactivate(component: AdminConfigComponent, route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean>|Promise<boolean>|boolean {
        let observer: Observer<boolean>;
        let observable: Observable<boolean> = Observable.create((_observer: Observer<boolean>) => {
            observer = _observer;
        });

        let resolve = function (action: DialogAction, dialog: Dialog) {
            observer.next(action.name == "Yes");
            observer.complete();
            dialog.hide();
        };

        if (component.dirty) {
            this.dialogSupport.open(new Dialog()
                .addTitle("Information")
                .addContent("Unsaved changes detected. Would you like to cancel them?")
                .addAction(new DialogAction("Cancel", "default", resolve))
                .addAction(new DialogAction("Yes", "warning", resolve))
            );
            return observable;
        }

        return true;
    }
}