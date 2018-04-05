package com.gemvietnam.base.mapper;
//
//
////import com.mobandme.android.transformer.Transformer;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Model Objects mapper
// * Created by neo on 8/30/2016.
// */
//public class ModelMapper {
//  private static org.modelmapper.ModelMapper mModelMapper;
//
//  // Get Singleton instance of Mapper
//  private static org.modelmapper.ModelMapper getMapper() {
//    if (mModelMapper == null) {
//      mModelMapper = createMapper();
//    }
//
//
//    return mModelMapper;
//  }
//
//  private static org.modelmapper.ModelMapper createMapper() {
//    return new org.modelmapper.ModelMapper();
//  }
//
//  public static <S, D> D map(S source, Class<D> dClass) {
//    return getMapper().map(source, dClass);
//  }
//
//  public static <S, D> List<D> mapList(List<S> list, Class<D> dClass) {
//    List<D> dList = new ArrayList<>();
//    for (S item : list) {
//      dList.add(getMapper().map(item, dClass));
//    }
//
//    return dList;
//  }
//}
