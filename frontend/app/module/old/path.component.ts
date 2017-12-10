export let PathsFactory = ((appParams: "APP_PARAMS") => {
    return new Paths((<any> appParams)["BACKEND_MODE"]);
});

export class Paths {
    private _itemPaths: ITEM;

    constructor(backendMode: string) {
        this._itemPaths = new ITEM(backendMode);
    }

    public get ITEM(): ITEM {
        return this._itemPaths;
    }

}

class ITEM {
    private _env: any = {
        "getAll": {
            real: "http://localhost:7001/home-maintenance/item/getAll",
            mock: "/angular2/frontend/mock/items.json"
        },
        "save": {
            real: "http://localhost:7001/home-maintenance/item/save",
            mock: null
        }
    };

    constructor(private mode: string) {}

    public get GET_ALL(): string {
        return this._env["getAll"][this.mode];
    }

    public get SAVE_ITEM(): string {
        return this._env["save"][this.mode];
    }

}