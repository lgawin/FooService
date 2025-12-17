# FooService

Sample service that is "dynamically" added to SystemServer services.

`service` contains 
* `IFooService.aidl` defining service interface
* `FooService.java` providing service implementation
* `FooServiceOverlay` that registers `ev.gawluk.service.FooService` as a service (using `config_deviceSpecificSystemServices`)

Then `FooManager` in `client` provides interface to access it, e.g.
1. add `FooClient` as static lib

```
android_app {
    name: "MyApp",
    static_libs: [ 
        "FooClient",
    ]
    ...
}  
```

2. instantiate and use `FooManager`

```java
    FooManager fooManager = FooManager.getInstance(this);
    if (fooManager != null) {
        fooManager.doWork();
    }
```

## Setup

### adjust repo

First you need to instruct repo to add `FooService` project to your AOSP sources.

Copy `local_manifests/*.xml` into your AOSP's `.repo/local_manifests`.

```shell
AOSP_ROOT=/sources/aosp

mkdir -p $AOSP_ROOT/.repo/local_manifests
cp ./local_manifests/*.xml $AOSP_ROOT/.repo/local_manifests/
```

### sync repo

Sync sources, at least this project, e.g.
```shell
repo sync frameworks/opt/FooService
```

### add stuff to device

`FooService`, `FooClient` and SELinux rules need to be added, e.g. to `packages/services/Car/car_product` using

```shell
git -C packages/services/Car/car_product apply $(realpath ./frameworks/opt/FooService/car_product.patch)
```

invoked in your AOSP sources root.
