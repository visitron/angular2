(function (global) {
    System.config({
        transpiler: 'ts',
        paths: {
            'npm:': 'node_modules/'
        },
        // meta: {
        //     'typescript': {
        //         format: 'cjs',
        //         exports: 'ts'
        //     }
        // },
        meta: {
            '@types/slickgrid': {
                format: 'esm'
            },
            '@types/slickgrid/slick.rowselectionmodel': {
                format: 'esm'
            },
            '@types/underscore': {
                format: 'esm',
                exports: '_'
            },
        },
        map: {
            app: 'build',
            // angular bundles
            '@angular/core': 'npm:@angular/core/bundles/core.umd.js',
            '@angular/common': 'npm:@angular/common/bundles/common.umd.js',
            '@angular/compiler': 'npm:@angular/compiler/bundles/compiler.umd.js',
            '@angular/platform-browser': 'npm:@angular/platform-browser/bundles/platform-browser.umd.js',
            '@angular/platform-browser-dynamic': 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
            '@angular/http': 'npm:@angular/http/bundles/http.umd.js',
            '@angular/router': 'npm:@angular/router/bundles/router.umd.js',
            '@angular/forms': 'npm:@angular/forms/bundles/forms.umd.js',
            // other libraries
            'rxjs': 'npm:rxjs',
            'angular-in-memory-web-api': 'npm:angular-in-memory-web-api',
            'angular2-ui-switch': 'npm:angular2-ui-switch/dist',
            'moment': 'npm:moment/moment.js',
            'underscore': 'npm:underscore/underscore.js',
            'tinycolor': 'npm:tinycolor2/tinycolor.js',
            'bootstrap': 'npm:bootstrap/dist/js/bootstrap.js',
            'jquery': 'npm:jquery/dist/jquery.js',
            'slickgrid-core': 'npm:slickgrid/slick.core.js',
            'slickgrid-grid': 'npm:slickgrid/slick.grid.js',
            'slickgrid-dataview': 'npm:slickgrid/slick.dataview.js',
            'slickgrid-cellrangeselector': 'npm:slickgrid/plugins/slick.cellrangeselector.js',
            'slickgrid-cellrangedecorator': 'npm:slickgrid/plugins/slick.cellrangedecorator.js',
            'slickgrid-rowselectionmodel': 'npm:slickgrid/plugins/slick.rowselectionmodel.js',
            'slickgrid-jquery-event-drag': 'npm:slickgrid/lib/jquery.event.drag-2.3.0.js',
            //@types
            '@types/underscore': 'npm:@types/underscore/index.d.ts',
            '@types/slickgrid': 'npm:@types/slickgrid/index.d.ts',
            '@types/tinycolor': 'npm:@types/tinycolor/index.d.ts',
            '@types/slickgrid/slick.rowselectionmodel': 'npm:@types/slickgrid/slick.rowselectionmodel.d.ts',
            '@types/jquery': 'npm:jquery/index.d.ts',
            '@types/bootstrap': 'npm:@types/bootstrap/index.d.ts',
            'typescript': 'npm:typescript',
            'ts': 'npm:plugin-typescript/lib/plugin.js'
        },
        // packages tells the System loader how to load when no filename and/or no extension
        packages: {
            app: {
                main: 'main.boot',
                defaultExtension: 'js'
            },
            rxjs: {
                defaultExtension: 'js'
            },
            'angular-in-memory-web-api': {
                main: './index',
                defaultExtension: 'js'
            },
            'angular2-ui-switch': {
                main: 'index',
                defaultExtension: 'js'
            },
            // "ts": {
            //     "main": "plugin.js"
            // },
            "typescript": {
                "main": "lib/typescript.js",
                "meta": {
                    "lib/typescript.js": {
                        "exports": "ts"
                    }
                }
            }
        }
    });
})(this);