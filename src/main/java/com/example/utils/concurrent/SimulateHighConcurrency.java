package com.example.utils.concurrent;


import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.concurrent.*;

@RestController
@Slf4j
public class SimulateHighConcurrency {


    ExecutorService threadPool = new ThreadPoolExecutor(10,10,0L,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10000),
            Executors.defaultThreadFactory() ,new ThreadPoolExecutor.CallerRunsPolicy());


    @PostMapping(value = "/param")
    public void sendKafka(@RequestBody @Validated Param param) {
        log.info("入参：{}", JSONUtil.toJsonStr(param));
        new Thread(() -> {
            this.send(param);
        }).start();
    }

    public void send(Param param) {
        log.info("开始发送");
        Long minute = param.getMinute();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(param.getConcurrenceNum());
        if (minute != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime targetTime = now.plusMinutes(minute);
            while (now.isBefore(targetTime)) {
                threadSend(param, cyclicBarrier);
                now = LocalDateTime.now();
            }
        } else {
            for (long i = 0; i < param.getLoopNum(); i++) {
                threadSend(param, cyclicBarrier);
            }
        }
        log.info("发送完毕");
    }

    private void threadSend(Param param, CyclicBarrier cyclicBarrier) {
        for (int j = 0; j < param.getConcurrenceNum(); j++) {
            threadPool.execute(() -> {
                try {
                    cyclicBarrier.await();
//                   doSomething();
                } catch (Exception e) {
                    log.error("发送kafka异常", e);
                }
            });
        }
    }
}