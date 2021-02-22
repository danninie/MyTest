import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './payment.reducer';
import { IPayment } from 'app/shared/model/payment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPaymentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PaymentDetail = (props: IPaymentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { paymentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="tryPaymentApp.payment.detail.title">Payment</Translate> [<b>{paymentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="bidForStoryTreeId">
              <Translate contentKey="tryPaymentApp.payment.bidForStoryTreeId">Bid For Story Tree Id</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.bidForStoryTreeId}</dd>
          <dt>
            <span id="bidderID">
              <Translate contentKey="tryPaymentApp.payment.bidderID">Bidder ID</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.bidderID}</dd>
          <dt>
            <span id="storyTreeID">
              <Translate contentKey="tryPaymentApp.payment.storyTreeID">Story Tree ID</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.storyTreeID}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="tryPaymentApp.payment.price">Price</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.price}</dd>
          <dt>
            <span id="clientSecret">
              <Translate contentKey="tryPaymentApp.payment.clientSecret">Client Secret</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.clientSecret}</dd>
          <dt>
            <span id="paymentState">
              <Translate contentKey="tryPaymentApp.payment.paymentState">Payment State</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.paymentState}</dd>
          <dt>
            <span id="paymentMessage">
              <Translate contentKey="tryPaymentApp.payment.paymentMessage">Payment Message</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.paymentMessage}</dd>
          <dt>
            <span id="createTime">
              <Translate contentKey="tryPaymentApp.payment.createTime">Create Time</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.createTime ? <TextFormat value={paymentEntity.createTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateTime">
              <Translate contentKey="tryPaymentApp.payment.updateTime">Update Time</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.updateTime ? <TextFormat value={paymentEntity.updateTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/payment" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payment/${paymentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ payment }: IRootState) => ({
  paymentEntity: payment.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PaymentDetail);
