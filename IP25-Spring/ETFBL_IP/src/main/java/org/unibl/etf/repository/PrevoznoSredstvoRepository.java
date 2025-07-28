package org.unibl.etf.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.model.PrevoznoSredstvo;
import org.unibl.etf.model.TipPrevoznogSredstva;

import java.util.List;

public interface PrevoznoSredstvoRepository extends JpaRepository<PrevoznoSredstvo, Integer> {
    List<PrevoznoSredstvo> findByTip(TipPrevoznogSredstva tip);
}
