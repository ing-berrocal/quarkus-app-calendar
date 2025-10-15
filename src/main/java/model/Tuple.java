package model;

import java.time.LocalTime;
import java.util.Comparator;

/**
 *
 * @author DELL
 */
public record Tuple(Integer time,Integer durationInMinutes,int count) implements Comparator<Tuple>{
        
        public Tuple removeServiceInstance(){
            return new Tuple(this.time, this.durationInMinutes,(this.count-1));
        }

        public String getTimeFormatted(){
            return LocalTime.of(0, 0).plusMinutes(this.time).toString();
        }

        public Boolean isValid(){
            return count > 0;
        }

        public Tuple inactive() {
            return new Tuple(this.time, this.durationInMinutes,0);
        }

        @Override
        public int compare(Tuple o1, Tuple o2) {
            // TODO Auto-generated method stub
            return Integer.compare(o1.time(), o2.time());
        }
        
}