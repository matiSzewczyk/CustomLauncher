package pl.matis.customlauncher.testing.data

import com.matis.customlauncher.model.view.ApplicationInfoViewDto

val applications: List<ApplicationInfoViewDto> = listOf(
    ApplicationInfoViewDto(
        packageName = "com.foo",
        label = "Foo",
        isHomeScreenApplication = false
    ),
    ApplicationInfoViewDto(
        packageName = "com.bar",
        label = "Bar",
        isHomeScreenApplication = false
    ),
    ApplicationInfoViewDto(
        packageName = "com.baz",
        label = "Baz",
        isHomeScreenApplication = false
    )
)
