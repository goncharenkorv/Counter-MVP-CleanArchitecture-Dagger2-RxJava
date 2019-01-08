package gruv.apps.counter.domain.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Простая модель для уровня домена (внутренний уровень)
 *
 * @author Goncharenko Ruslan
 */
public class DomainModel {

    @NonNull
    private String mValue;

    public DomainModel(@NonNull String value) {
        mValue = value;
    }

    @NonNull
    public String getValue() {
        return mValue;
    }

    public void setValue(@NonNull String value) {
        mValue = value;
    }

    /**
     * Переопределяем метод equals
     * Это, в частности, необходимо для теста ValueUpdaterInteractorTest.onValueUpdateTest()
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DomainModel domainModel = (DomainModel) o;
        /*
        if (domainModel.mValue == null) {
            return (mValue == null);
        }
        */
        return domainModel.mValue.equals(mValue);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + '@' + "mValue=" + mValue;
    }
}
