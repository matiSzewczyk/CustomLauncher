package pl.matis.customlauncher.testing.data

import com.matis.customlauncher.model.domain.ApplicationInfoDto

val installedApplications: List<ApplicationInfoDto> = listOf(
    ApplicationInfoDto(packageName = "com.example", label = "Example"),
    ApplicationInfoDto(packageName = "com.foo", label = "Foo"),
    ApplicationInfoDto(packageName = "com.bar", label = "Bar"),
    ApplicationInfoDto(packageName = "com.baz", label = "Baz")
)
