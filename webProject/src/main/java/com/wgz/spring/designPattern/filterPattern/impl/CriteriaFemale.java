package com.wgz.spring.designPattern.filterPattern.impl;

import java.util.ArrayList;
import java.util.List;

import com.wgz.spring.designPattern.filterPattern.Criteria;
import com.wgz.spring.designPattern.filterPattern.Person;

public class CriteriaFemale implements Criteria {
 
   @Override
   public List<Person> meetCriteria(List<Person> persons) {
      List<Person> femalePersons = new ArrayList<Person>(); 
      for (Person person : persons) {
         if(person.getGender().equalsIgnoreCase("FEMALE")){
            femalePersons.add(person);
         }
      }
      return femalePersons;
   }
}