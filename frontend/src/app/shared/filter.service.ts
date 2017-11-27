import {Injectable} from '@angular/core';
import {Person} from '../admin/people/person';
import {Animal} from './animal';
import {FilterData} from './FilterData';

@Injectable()
export class FilterService {

  private filterByFirstName = 0;
  private filterByLastName = 1;
  private filterByBoth = 0;
  private filterByAnimalName = 0;
  private filterByAnimalType = 1;

  constructor() {
  }

  performPersonFilter(filter: string, data: Person[]): FilterData<Person> {
    return this.performFilter<Person>(filter, data, this.filterByPerson);
  }

  performAnimalFilter(filter: string, data: Animal[]): FilterData<Animal> {
    return this.performFilter<Animal>(filter, data, this.filterByAnimal);
  }

  private performFilter<T>(filter: string, data: Object[], filterByType): FilterData<T> {
    const filterBy: string[] = filter.toLocaleLowerCase().split(/\s/);
    const filteredData = filterByType(filterBy, data);
    return this.prepareMessage<T>(filteredData);
  }

  private filterByPerson = (filterBy: string[], data: Object[]): Object[] => {
    if (filterBy.length === 2) {
      return this.filterFirstAndLastNameSeparately(data, filterBy);
    } else if (filterBy.length === 1) {
      return this.filterFirstAndLastNameSimultaneously(data, filterBy);
    } else {
      return [];
    }
  };

  private filterByAnimal = (filterBy: string[], data: Object[]): Object[] => {
    if (filterBy.length === 2) {
      return this.filterAnimalNameAndType(data, filterBy);
    } else if (filterBy.length === 1) {
      return this.filterAnimalName(data, filterBy);
    } else {
      return [];
    }
  };

  private prepareMessage<T>(filteredData: Object[]): FilterData<T> {
    return {
      data: <T[]>filteredData,
      message: filteredData.length === 0 ? 'Data not found' : null
    };
  }

  private filterFirstAndLastNameSeparately(people: Object[], filterBy: string[]): Object[] {
    return people.filter((person: Person) => {
      return person.firstName.toLocaleLowerCase().indexOf(filterBy[this.filterByFirstName]) !== -1
        && person.lastName.toLocaleLowerCase().indexOf(filterBy[this.filterByLastName]) !== -1;
    });
  }

  private filterFirstAndLastNameSimultaneously(people: Object[], filterBy: string[]): Object[] {
    return people.filter((person: Person) => {
      return person.firstName.toLocaleLowerCase().indexOf(filterBy[this.filterByBoth]) !== -1
        || person.lastName.toLocaleLowerCase().indexOf(filterBy[this.filterByBoth]) !== -1;
    });
  }

  private filterAnimalName(people: Object[], filterBy: string[]): Object[] {
    return people.filter((animal: Animal) => {
      return animal.name.toLocaleLowerCase().indexOf(filterBy[this.filterByAnimalName]) !== -1;
    });
  }

  private filterAnimalNameAndType(people: Object[], filterBy: string[]): Object[] {
    return people.filter((animal: Animal) => {
      return animal.name.toLocaleLowerCase().indexOf(filterBy[this.filterByAnimalName]) !== -1
        && animal.type.toLocaleLowerCase().indexOf(filterBy[this.filterByAnimalType]) !== -1;
    });
  }
}
