import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './payment.reducer';
import { IPayment } from 'app/shared/model/payment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPaymentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Payment = (props: IPaymentProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { paymentList, match, loading } = props;
  return (
    <div>
      <h2 id="payment-heading">
        <Translate contentKey="tryPaymentApp.payment.home.title">Payments</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="tryPaymentApp.payment.home.createLabel">Create new Payment</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {paymentList && paymentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="tryPaymentApp.payment.bidForStoryTreeId">Bid For Story Tree Id</Translate>
                </th>
                <th>
                  <Translate contentKey="tryPaymentApp.payment.bidderID">Bidder ID</Translate>
                </th>
                <th>
                  <Translate contentKey="tryPaymentApp.payment.storyTreeID">Story Tree ID</Translate>
                </th>
                <th>
                  <Translate contentKey="tryPaymentApp.payment.price">Price</Translate>
                </th>
                <th>
                  <Translate contentKey="tryPaymentApp.payment.clientSecret">Client Secret</Translate>
                </th>
                <th>
                  <Translate contentKey="tryPaymentApp.payment.paymentState">Payment State</Translate>
                </th>
                <th>
                  <Translate contentKey="tryPaymentApp.payment.paymentMessage">Payment Message</Translate>
                </th>
                <th>
                  <Translate contentKey="tryPaymentApp.payment.createTime">Create Time</Translate>
                </th>
                <th>
                  <Translate contentKey="tryPaymentApp.payment.updateTime">Update Time</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {paymentList.map((payment, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${payment.id}`} color="link" size="sm">
                      {payment.id}
                    </Button>
                  </td>
                  <td>{payment.bidForStoryTreeId}</td>
                  <td>{payment.bidderID}</td>
                  <td>{payment.storyTreeID}</td>
                  <td>{payment.price}</td>
                  <td>{payment.clientSecret}</td>
                  <td>{payment.paymentState}</td>
                  <td>{payment.paymentMessage}</td>
                  <td>{payment.createTime ? <TextFormat type="date" value={payment.createTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{payment.updateTime ? <TextFormat type="date" value={payment.updateTime} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${payment.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${payment.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${payment.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="tryPaymentApp.payment.home.notFound">No Payments found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ payment }: IRootState) => ({
  paymentList: payment.entities,
  loading: payment.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Payment);
