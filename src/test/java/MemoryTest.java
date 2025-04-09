import org.junit.jupiter.api.Test;
import 컨벤션.개선된컨벤션.UserDto;
import 컨벤션.잘못된컨벤션.userDTO;

import java.util.ArrayList;
import java.util.List;

public class MemoryTest {
    @Test
    void compareMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

        List<userDTO> oldStyleObjects = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            oldStyleObjects.add(new userDTO());
        }

        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("Memory used by old style: %d bytes%n", usedMemoryAfter - usedMemoryBefore);

        oldStyleObjects = null;
        System.gc();

        usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

        List<UserDto> newStyleObjects = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            newStyleObjects.add(new UserDto());
        }

        usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("Memory used by new style: %d bytes%n", usedMemoryAfter - usedMemoryBefore);
    }

}
