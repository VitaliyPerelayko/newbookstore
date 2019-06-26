import {Category} from '../../emums/category.enum';

export class Book {
  id: bigint;

  name: string;

  description: string;

  authors: Array<{id: bigint,
  name: string}>;

  publishDate: string;

  publisher: {name: string, id: bigint};

  price: number;

  category: Category;
}
