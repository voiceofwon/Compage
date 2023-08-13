package CompWeb.Homepage.Service;


import CompWeb.Homepage.Entity.Visitor;
import CompWeb.Homepage.Repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


//일정시간마다 redis에 임시저장된 접속자 정보를 DB로 옮기는 동작 schdule + visitor 수 조회
@Component
@Slf4j
@RequiredArgsConstructor
public class VisitorScheduler {

    private final RedisTemplate<String,String> redisTemplate;

    private final VisitorRepository visitorRepository;

    //30분마다 redis에 임시 저장된 접속자 정보를 DB로 옮기는 Scheduling
    @Scheduled(initialDelay = 3000, fixedDelay = 1800000)
    public void updateVisitorData(){
        Set<String> keys = redisTemplate.keys("*_*");

        for(String key:keys){
            String[] parts = key.split("_");
            String userIp = parts[0];
            LocalDate date = LocalDate.parse(parts[1]);

            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

            String userAgent = valueOperations.get(key);

            if(!visitorRepository.existsByUserIpAndDate(userIp, date)){
                Visitor visitor = Visitor.builder()
                        .userIp(userIp)
                        .date(date)
                        .build();

                visitorRepository.save(visitor);

            }

            redisTemplate.delete(key);
        }
    }

    //누적 접속자 수
    public long TotalVisitor(){
        return visitorRepository.count();
    }

    //금일 접속자 수
    public int DayVisitor(LocalDate date){
        return visitorRepository.findByDate(date).size();
    }

    //이번달 접속자 수
    public int MonthVisitor(int Year,int Month){
        int count =0;
        for(int i=1;i<LocalDate.of(Year,Month,1).lengthOfMonth();i++) {
            count += visitorRepository.findByDate(LocalDate.of(Year, Month, i)).size();
        }
        return count;
    }

    //금년 월별 접속자 수
    public int[] ThisYearMonth(){
        int[] month = new int[12];
        LocalDate date = LocalDate.now();
        for(int i=0; i<date.getMonthValue();i++){
            month[i] = MonthVisitor(date.getYear(),i+1);
        }
        return month;
    }


}
