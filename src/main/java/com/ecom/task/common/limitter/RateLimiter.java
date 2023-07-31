package com.ecom.task.common.limitter;


import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * В этой реализации лимитера я использую скользящее окно размером в перод времени, чтобы динамически
 * выделять слот на посещение для пользователя. Такое решение позволяет точно выдерживать количество
 * посещений в выделенный период времени. При возможности послабления условий, немного большего коли-
 * чества посещений за период, либо запрет на посещение при не строгом соблюдении периода, возможно
 * использования более экономного в ресурсах алгоритма.
 */
@RequiredArgsConstructor
public class RateLimiter {
    static final long NANOS_IN_MINUTE = 60_000_000_000L;

    @Getter
    @Setter
    private VisitLinkedList lastVisit = null;
    private final long maxRequests;
    private final long timePeriod; // размер окна проверки

    public boolean checkRequest() {
        var startTime = System.nanoTime() - timePeriod * NANOS_IN_MINUTE;
        if (calculateVisits(lastVisit, startTime) < maxRequests) {
            var newVisit = new VisitLinkedList(System.nanoTime());
            newVisit.setPrevVisit(lastVisit);
            lastVisit = newVisit;
            return true;
        }

        return false;
    }

    private Long calculateVisits(VisitLinkedList visit, Long startTime) {
        if (visit == null) return 0L;

        if (visit.visitTime < startTime) {
            //если текущее время визита раньше окна проверки значит оно нам не нужно, отрезаем
            visit.trim();
            return 0L;
        }

        return 1 + calculateVisits(visit.prevVisit, startTime);
    }

    @Data
    static class VisitLinkedList {
        private final long visitTime;
        private VisitLinkedList prevVisit;

        public void trim() {
            this.prevVisit = null;
        }

    }
}
