package com.matis.customlauncher.domain.appsearch

import com.matis.customlauncher.domain.fakes.TestInstalledApplicationsRepository
import com.matis.customlauncher.model.domain.ApplicationInfoDto
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetApplicationsMatchingQueryTest {

    private val repository = TestInstalledApplicationsRepository()

    private lateinit var getApplicationsMatchingQuery: GetApplicationsMatchingQuery

    @Before
    fun setUp() {
        getApplicationsMatchingQuery = GetApplicationsMatchingQuery(
            repository = repository
        )
    }

    @Test
    fun `Should return list of applications sorted alphabetically by their label`() = runTest {
        // Given
        val foo = ApplicationInfoDto(packageName = "com.example.foo", label = "foo")
        val bar = ApplicationInfoDto(packageName = "com.example.bar", label = "bar")
        val baz = ApplicationInfoDto(packageName = "com.example.baz", label = "baz")
        val unsortedApplications = listOf(baz, foo, bar)
        repository.applications.value = unsortedApplications

        // When
        val result = getApplicationsMatchingQuery("").first()

        // Then
        val sortedApplications = listOf(bar, baz, foo)
        assertEquals(result, sortedApplications)
    }

    @Test
    fun `Should return list with applications that have a label matching the query`() = runTest {
        // Given
        val foo = ApplicationInfoDto(packageName = "com.example.foo", label = "foo")
        val bar = ApplicationInfoDto(packageName = "com.example.bar", label = "bar")
        val baz = ApplicationInfoDto(packageName = "com.example.baz", label = "baz")
        val applications = listOf(foo, bar, baz)
        repository.applications.value = applications

        // When
        val result = getApplicationsMatchingQuery("b").first()

        // Then
        val applicationsMatchingQuery = listOf(bar, baz)
        assertEquals(result, applicationsMatchingQuery)
        assertFalse(foo in applicationsMatchingQuery)
    }
}
