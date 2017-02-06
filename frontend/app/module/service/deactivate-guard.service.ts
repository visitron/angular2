import {CanDeactivate, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {AdminConfigComponent} from "../admin/admin-config.component";
import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";

@Injectable()
export class DeactivateGuard implements CanDeactivate<AdminConfigComponent> {

    canDeactivate(component: AdminConfigComponent, route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean>|Promise<boolean>|boolean {

        return !component.dirty;
    }
}