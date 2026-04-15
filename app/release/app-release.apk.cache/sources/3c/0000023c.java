package c3;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import s2.b;

/* loaded from: classes.dex */
public final class a extends b3.a {
    @Override // b3.a
    public final Random b() {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        b.u(current, "current()");
        return current;
    }
}