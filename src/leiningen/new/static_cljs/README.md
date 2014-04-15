# {{name}}

## Run a dev server

```
lein ring server
```

ClojureScript and Less will be recompiled on-the-fly as you make changes.


## Export static site for production

```
lein export
```

Activates the `prod` profile and runs the `build.core/export` function.

## Config

See `config/dev/config.clj` and `config/prod/config.clj` for env-based config.
Use the `prod` and `dev` profiles to activate. Note: `lein export` automatically
activates the `prod` profile so you don't have to worry about it. By default:

- the dev profile is active during dev
- the prod profile is active during site build
