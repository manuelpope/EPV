package com.calsol.solar.service;

import com.calsol.solar.domain.entity.Design;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiPredicate;

/**
 * The type Context design.
 */
@Data
@Service
@Slf4j
public class ContextDesign {
    /**
     * The Local date time entry bi predicate.
     */
    final BiPredicate<LocalDateTime, Entry<String, Design>> localDateTimeEntryBiPredicate = (hourAgo1, entry1) -> entry1.getValue().getLocalDateTime().minusHours(1L).isBefore(hourAgo1);
    private final ZoneId zoneId = TimeZone.getTimeZone("UTC").toZoneId();
    private Map<String, Design> context;

    /**
     * Instantiates a new Context design.
     */
    public ContextDesign() {
        context = new ConcurrentHashMap<>();
    }

    /**
     * Gets design.
     *
     * @param nameDesign the name design
     * @return the design
     * @throws Exception the exception
     */
    public Design getDesign(String nameDesign) throws Exception {
        assertExistenceDesign(nameDesign);
        return context.get(nameDesign);

    }

    private void assertExistenceDesign(String nameDesign) throws Exception {
        if (!context.containsKey(nameDesign)) {
            throw new Exception("There is no design with that name");
        }
    }

    /**
     * Add design.
     *
     * @param design the design
     * @throws Exception the exception
     */
    public synchronized void addDesign(Design design) throws Exception {
        log.info("Adding " + design.getName());
        if (context.containsKey(design.getName())) {
            throw new Exception("its already set that name");
        }
        context.put(design.getName(), design);

    }

    /**
     * Update.
     *
     * @param design the design
     * @throws Exception the exception
     */
    public void update(Design design) throws Exception {
        log.info("update " + design.getName());
        assertExistenceDesign(design.getName());
        context.put(design.getName(), design);

    }

    /**
     * House keeping.
     *
     * @throws Exception the exception
     */
    @Async
    public void houseKeeping() throws Exception {
        log.info("Performing House Keeping of non sync designs");
        LocalDateTime hourAgo = LocalDateTime.now(this.zoneId).minusHours(1L);
        List<String> listToCleanDesign = new ArrayList<>();
        for (Entry<String, Design> entry : this.context.entrySet()) {

            if (localDateTimeEntryBiPredicate.test(hourAgo, entry)) {
                listToCleanDesign.add(entry.getKey());
            }
        }
        log.info("count elements to be removed: " + listToCleanDesign.size());
        for (String key : listToCleanDesign) {
            this.context.remove(key);
        }

    }

    /**
     * Schedule fixed delay task.
     *
     * @throws Exception the exception
     */
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public synchronized void scheduleTaskCleaningDecorator() throws Exception {
        log.info("Fixed Rate task - " + System.currentTimeMillis() / 1000);
        this.houseKeeping();

    }
}
