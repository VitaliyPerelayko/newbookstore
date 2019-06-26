export class BookRequest {
  id = null;

  name: string;

  description: string;

  authorsId = new Array<bigint>();

  publishDate: string;

  publisherId: bigint;

  price: number;

  category: number;
}
