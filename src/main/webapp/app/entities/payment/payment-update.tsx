import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './payment.reducer';
import { IPayment } from 'app/shared/model/payment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPaymentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PaymentUpdate = (props: IPaymentUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { paymentEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/payment');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createTime = convertDateTimeToServer(values.createTime);
    values.updateTime = convertDateTimeToServer(values.updateTime);

    if (errors.length === 0) {
      const entity = {
        ...paymentEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tryPaymentApp.payment.home.createOrEditLabel">
            <Translate contentKey="tryPaymentApp.payment.home.createOrEditLabel">Create or edit a Payment</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : paymentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="payment-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="payment-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="bidForStoryTreeIdLabel" for="payment-bidForStoryTreeId">
                  <Translate contentKey="tryPaymentApp.payment.bidForStoryTreeId">Bid For Story Tree Id</Translate>
                </Label>
                <AvField id="payment-bidForStoryTreeId" type="string" className="form-control" name="bidForStoryTreeId" />
              </AvGroup>
              <AvGroup>
                <Label id="bidderIDLabel" for="payment-bidderID">
                  <Translate contentKey="tryPaymentApp.payment.bidderID">Bidder ID</Translate>
                </Label>
                <AvField id="payment-bidderID" type="string" className="form-control" name="bidderID" />
              </AvGroup>
              <AvGroup>
                <Label id="storyTreeIDLabel" for="payment-storyTreeID">
                  <Translate contentKey="tryPaymentApp.payment.storyTreeID">Story Tree ID</Translate>
                </Label>
                <AvField id="payment-storyTreeID" type="string" className="form-control" name="storyTreeID" />
              </AvGroup>
              <AvGroup>
                <Label id="priceLabel" for="payment-price">
                  <Translate contentKey="tryPaymentApp.payment.price">Price</Translate>
                </Label>
                <AvField id="payment-price" type="string" className="form-control" name="price" />
              </AvGroup>
              <AvGroup>
                <Label id="clientSecretLabel" for="payment-clientSecret">
                  <Translate contentKey="tryPaymentApp.payment.clientSecret">Client Secret</Translate>
                </Label>
                <AvField id="payment-clientSecret" type="text" name="clientSecret" />
              </AvGroup>
              <AvGroup>
                <Label id="paymentStateLabel" for="payment-paymentState">
                  <Translate contentKey="tryPaymentApp.payment.paymentState">Payment State</Translate>
                </Label>
                <AvField id="payment-paymentState" type="text" name="paymentState" />
              </AvGroup>
              <AvGroup>
                <Label id="paymentMessageLabel" for="payment-paymentMessage">
                  <Translate contentKey="tryPaymentApp.payment.paymentMessage">Payment Message</Translate>
                </Label>
                <AvField id="payment-paymentMessage" type="text" name="paymentMessage" />
              </AvGroup>
              <AvGroup>
                <Label id="createTimeLabel" for="payment-createTime">
                  <Translate contentKey="tryPaymentApp.payment.createTime">Create Time</Translate>
                </Label>
                <AvInput
                  id="payment-createTime"
                  type="datetime-local"
                  className="form-control"
                  name="createTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.paymentEntity.createTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updateTimeLabel" for="payment-updateTime">
                  <Translate contentKey="tryPaymentApp.payment.updateTime">Update Time</Translate>
                </Label>
                <AvInput
                  id="payment-updateTime"
                  type="datetime-local"
                  className="form-control"
                  name="updateTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.paymentEntity.updateTime)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/payment" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  paymentEntity: storeState.payment.entity,
  loading: storeState.payment.loading,
  updating: storeState.payment.updating,
  updateSuccess: storeState.payment.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PaymentUpdate);
