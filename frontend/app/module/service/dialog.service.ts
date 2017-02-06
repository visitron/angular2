import {Injectable} from "@angular/core";

@Injectable()
export class DialogSupport {

    public createDialog(): Dialog {
        return new Dialog;
    }

}

export class Dialog {
    private _title: string;
    private _content: string;
    private _actions: DialogAction[];

    public addTitle(title: string): this {
        return this;
    }

    public addContent(content: string): this {
        return this;
    }

    public addAction(action: DialogAction): this {
        return this;
    }

    public show() {

    }
}

export class DialogAction {
    public constructor(private name: string, private callback: () => void) {}
}