import {Injectable} from "@angular/core";
import "rxjs/add/operator/map";
import {CONFIG} from "../main.module";

@Injectable()
export class ConfigProvider {

    private _mockMode: boolean = CONFIG.mode === 'mock';

    public get host(): string {
        return this._mockMode ? CONFIG.mock : CONFIG.remote;
    }

    public get isMockMode(): boolean {
        return this._mockMode;
    }

}