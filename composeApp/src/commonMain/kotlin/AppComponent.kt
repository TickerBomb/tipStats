import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import domain.AppPreferences
import domain.AppPreferencesImpl
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus

interface CoroutinesComponent {
    val mainImmediateDispatcher: CoroutineDispatcher
    val applicationScope: CoroutineScope
}

internal class CoroutinesComponentImpl private constructor() : CoroutinesComponent {

    companion object {
        fun create(): CoroutinesComponent = CoroutinesComponentImpl()
    }

    override val mainImmediateDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
    override val applicationScope: CoroutineScope
        get() = CoroutineScope(
            SupervisorJob() + mainImmediateDispatcher,
        )
}

interface CoreComponent : CoroutinesComponent {
    val appPreferences: AppPreferences
}

internal class CoreComponentImpl internal constructor() : CoreComponent, CoroutinesComponent by CoroutinesComponentImpl.create() {
    private val dataStore: DataStore<Preferences> = dataStorePreferences(
        corruptionHandler = null,
        coroutineScope = applicationScope + Dispatchers.IO,
        migrations = emptyList()
    )
    override val appPreferences: AppPreferences
        get() = AppPreferencesImpl(dataStore)

}

object ApplicationComponent {
    private var _coreComponent: CoreComponent? = null
    val coreComponent
        get() = _coreComponent ?: throw IllegalStateException("Make sure to call ApplicationComponent.init()")

    fun init() {
        _coreComponent = CoreComponentImpl()
    }
}

val coreComponent get() = ApplicationComponent.coreComponent
val logger = KotlinLogging.logger {}
