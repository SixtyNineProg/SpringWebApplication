package by.clevertec.WebApplication.сache;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class PriorityQueueObject {
    private String key;
    private long time;
}
