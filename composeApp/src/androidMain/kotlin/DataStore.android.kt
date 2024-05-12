import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import com.ticker.tipstats.applicationContext
import kotlinx.coroutines.CoroutineScope
import java.io.File

actual fun dataStorePreferences(
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>?,
    coroutineScope: CoroutineScope,
    migrations: List<DataMigration<Preferences>>,
): DataStore<Preferences> = createDataStore(
    corruptionHandler = corruptionHandler,
    coroutineScope = coroutineScope,
    migrations = migrations,
    path = {
        File(applicationContext.filesDir, "datastore/$TIP_STATS_PREFERENCE_DATASTORE").path
    }
)

