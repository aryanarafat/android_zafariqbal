package d0;

import android.view.View;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import d.t0;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class b implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    public final /* synthetic */ int f2466a;

    /* renamed from: b  reason: collision with root package name */
    public final int f2467b;

    /* renamed from: c  reason: collision with root package name */
    public final Object f2468c;

    /* renamed from: d  reason: collision with root package name */
    public final Object f2469d;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public b(int i4, ArrayList arrayList) {
        this(arrayList, i4, null);
        this.f2466a = 1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i4 = this.f2466a;
        int i5 = 0;
        int i6 = this.f2467b;
        Object obj = this.f2468c;
        switch (i4) {
            case 0:
                s2.b bVar = (s2.b) ((t0) obj).f2437b;
                if (bVar != null) {
                    bVar.D0(i6);
                    return;
                }
                return;
            case 1:
                List list = (List) obj;
                int size = list.size();
                if (i6 != 1) {
                    while (i5 < size) {
                        ((androidx.emoji2.text.j) list.get(i5)).a();
                        i5++;
                    }
                    return;
                }
                while (i5 < size) {
                    ((androidx.emoji2.text.j) list.get(i5)).b();
                    i5++;
                }
                return;
            default:
                ((BottomSheetBehavior) this.f2469d).H((View) obj, i6, false);
                return;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public b(androidx.emoji2.text.j r3, int r4) {
        /*
            r2 = this;
            r0 = 1
            r2.f2466a = r0
            androidx.emoji2.text.j[] r0 = new androidx.emoji2.text.j[r0]
            if (r3 == 0) goto L13
            r1 = 0
            r0[r1] = r3
            java.util.List r3 = java.util.Arrays.asList(r0)
            r0 = 0
            r2.<init>(r3, r4, r0)
            return
        L13:
            java.lang.NullPointerException r3 = new java.lang.NullPointerException
            java.lang.String r4 = "initCallback cannot be null"
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: d0.b.<init>(androidx.emoji2.text.j, int):void");
    }

    public /* synthetic */ b(Object obj, Object obj2, int i4, int i5) {
        this.f2466a = i5;
        this.f2469d = obj;
        this.f2468c = obj2;
        this.f2467b = i4;
    }

    public b(List list, int i4, Throwable th) {
        this.f2466a = 1;
        if (list == null) {
            throw new NullPointerException("initCallbacks cannot be null");
        }
        this.f2468c = new ArrayList(list);
        this.f2467b = i4;
        this.f2469d = th;
    }
}