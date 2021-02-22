import { Moment } from 'moment';

export interface IPayment {
  id?: number;
  bidForStoryTreeId?: number;
  bidderID?: number;
  storyTreeID?: number;
  price?: number;
  clientSecret?: string;
  paymentState?: string;
  paymentMessage?: string;
  createTime?: string;
  updateTime?: string;
}

export const defaultValue: Readonly<IPayment> = {};
