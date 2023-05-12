package com.huanghehua.www.cinema.infrastructure.gatewayimpl;

import com.huanghehua.www.cinema.domain.gateway.ExhibitionGateWay;
import com.huanghehua.www.cinema.infrastructure.convertor.FilmConvertor;
import com.huanghehua.www.cinema.infrastructure.data.FilmPO;
import com.huanghehua.www.cinema.domain.exhibition.FilmModel;
import com.huanghehua.www.cinema.infrastructure.mapper.FilmMapper;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.common.PageAbility;

import java.util.List;

/**
 * 显示domain实现类
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
public class ExhibitionGateWayImpl implements ExhibitionGateWay {
    @Reference
    private FilmMapper filmMapper;

    @Override
    public List<FilmModel> listPageFilm(String name, PageAbility pageAbility) {
        // 先设置记录总数（以便完成逻辑分页），再获取开始索引值和当前页面大小
        Long count = filmMapper.countAllByName(name);
        pageAbility.setRecordCount(Math.toIntExact(count));
        Integer fromIndex = pageAbility.getFromIndex();
        Integer currentPageSize = pageAbility.getCurrentPageSize();

        List<FilmPO> films = filmMapper.listPageByName(name, fromIndex, currentPageSize);

        // 利用转换器进行类型转换, 并返回结果
        return FilmConvertor.poListToModel(films);
    }

    @Override
    public List<FilmModel> listFilm(String name) {
        List<FilmPO> films = filmMapper.listByName(name);

        // 利用转换器进行类型转换, 并返回结果
        return FilmConvertor.poListToModel(films);
    }

    @Override
    public FilmModel getFilm(Long id) {
        FilmPO filmPo = filmMapper.getFilmById(id);

        return FilmConvertor.poToModel(filmPo);
    }
}
