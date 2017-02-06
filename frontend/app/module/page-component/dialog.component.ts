import {Component} from "@angular/core";
import "rxjs/operator/map";
import {DialogSupport} from "../service/dialog.service";

@Component({
    selector: 'common-dialog',
    templateUrl: 'mockup/parts/dialog.html'
})
export class DialogComponent {
    private dialog: Dialog = new Dialog;

    constructor(private dialogSupport: DialogSupport) {
        dialogSupport.onOpen(this.onOpen.bind(this));
    }

    private onOpen(dialog: Dialog): void {
        this.dialog = dialog;
        this.dialog.show();
    }

    sendAction(action: DialogAction) {
        action.callback(action, this.dialog);
    }

}

export class Dialog {
    private _selector: JQuery = $('#common-dialog-id');
    public title: string;
    public content: string;
    public actions: DialogAction[] = [];

    public constructor(cancelable?: boolean, cancelName?: string) {
        if (cancelable) {
            this.actions.push(new DialogAction(_.isEmpty(cancelName) ? "Cancel" : cancelName, "default", () => {
                this._selector.modal('hide');
            }));
        }
    }

    public addTitle(title: string): this {
        this.title = title;
        return this;
    }

    public addContent(content: string): this {
        this.content = content;
        return this;
    }

    public addAction(action: DialogAction): this {
        this.actions.push(action);
        return this;
    }

    public show() {
        this._selector.modal('show');
    }

    public hide() {
        this._selector.modal('hide');
    }
}

type ButtonStyle = "default" | "danger" | "warning" | "primary";

export class DialogAction {
    public constructor(public name: string, public style: ButtonStyle, public callback: (action: DialogAction, dialog: Dialog) => void) {}
}