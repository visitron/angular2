(function (global) {
    System.config({
        paths: {
            'npm:': 'node_modules/'
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
            'moment': 'npm:moment',
            'color-js': 'npm:color-js'
        },
        // packages tells the System loader how to load when no filename and/or no extension
        packages: {
            build: {
                main: 'app.boot',
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
                main: './index',
                defaultExtension: 'js'
            },
            'moment': {
                main: './moment.js',
            },
            'color-js': {
                main: './color.js'
            }
        }
    });
})(this);