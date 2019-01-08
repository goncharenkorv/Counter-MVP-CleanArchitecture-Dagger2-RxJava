package gruv.apps.counter.domain.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import gruv.apps.counter.domain.model.DomainModel;
import gruv.apps.counter.storage.model.StorageModel;

/**
 * Простой репозиторий для установки значения, чтения значения и сохранения/восстановления
 *
 * @author Goncharenko Ruslan
 */
public interface Repository {
    @NonNull
    DomainModel getDomainModel();

    void setDomainModel(@NonNull DomainModel domainModel);

    /*
    * для тестов
    * */
    @NonNull
    StorageModel get();

    /*
     * для тестов
     * */
    void set(@NonNull StorageModel storageModel);

    void save(@NonNull Context context);

    void load(@NonNull Context context);
}
