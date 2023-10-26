
#include "GLMatrix.hpp"

GLMatrix::GLMatrix() {
  // Identitätsmatrix
  this->setColumn(0, GLVector(1.0f, 0.0f, 0.0f));
  this->setColumn(1, GLVector(0.0f, 1.0f, 0.0f));
  this->setColumn(2, GLVector(0.0f, 0.0f, 1.0f));
  this->setColumn(3, GLVector(0.0f, 0.0f, 0.0f));

  // Setzen der letzten Zeile (für homogene Koordinaten)
  mMatrix[3 * 4 + 0] = 0.0f;
  mMatrix[3 * 4 + 1] = 0.0f;
  mMatrix[3 * 4 + 2] = 0.0f;
  mMatrix[3 * 4 + 3] = 1.0f;
}

GLMatrix::GLMatrix(double p00, double p10, double p20, double p30, double p01, double p11, double p21, double p31, double p02, double p12, double p22, double p32, double p03, double p13, double p23, double p33) {
    mMatrix[0] = p00;
    mMatrix[1] = p10;
    mMatrix[2] = p20;
    mMatrix[3] = p30;
    mMatrix[4] = p01;
    mMatrix[5] = p11;
    mMatrix[6] = p21;
    mMatrix[7] = p31;
    mMatrix[8] = p02;
    mMatrix[9] = p12;
    mMatrix[10] = p22;
    mMatrix[11] = p32;
    mMatrix[12] = p03;
    mMatrix[13] = p13;
    mMatrix[14] = p23;
    mMatrix[15] = p33;
}

void GLMatrix::setColumn(int i, const GLPoint &rhs) {
  for (size_t j = 0; j < 3; ++j) {
    mMatrix[i + 4 * j] = rhs(j);
  }
}

void GLMatrix::setColumn(int i, const GLVector &rhs) {
  for (size_t j = 0; j < 3; ++j) {
    mMatrix[i + 4 * j] = rhs(j);
  }
}

void GLMatrix::setValue(const int row, const int column, const float value) {
  mMatrix[row * 4 + column] = value;
}

GLVector GLMatrix::getColumn(int i) const {
  return GLVector(mMatrix[i + 4 * 0], mMatrix[i + 4 * 1], mMatrix[i + 4 * 2]);
}

double GLMatrix::operator()(int row, int column) const {
  return mMatrix[row * 4 + column];
}

bool GLMatrix::inverse() {
  double inv[16], det;
  int i;

  inv[0] = mMatrix[5] * mMatrix[10] * mMatrix[15] -
           mMatrix[5] * mMatrix[11] * mMatrix[14] -
           mMatrix[9] * mMatrix[6] * mMatrix[15] +
           mMatrix[9] * mMatrix[7] * mMatrix[14] +
           mMatrix[13] * mMatrix[6] * mMatrix[11] -
           mMatrix[13] * mMatrix[7] * mMatrix[10];

  inv[4] = -mMatrix[4] * mMatrix[10] * mMatrix[15] +
           mMatrix[4] * mMatrix[11] * mMatrix[14] +
           mMatrix[8] * mMatrix[6] * mMatrix[15] -
           mMatrix[8] * mMatrix[7] * mMatrix[14] -
           mMatrix[12] * mMatrix[6] * mMatrix[11] +
           mMatrix[12] * mMatrix[7] * mMatrix[10];

  inv[8] = mMatrix[4] * mMatrix[9] * mMatrix[15] -
           mMatrix[4] * mMatrix[11] * mMatrix[13] -
           mMatrix[8] * mMatrix[5] * mMatrix[15] +
           mMatrix[8] * mMatrix[7] * mMatrix[13] +
           mMatrix[12] * mMatrix[5] * mMatrix[11] -
           mMatrix[12] * mMatrix[7] * mMatrix[9];

  inv[12] = -mMatrix[4] * mMatrix[9] * mMatrix[14] +
            mMatrix[4] * mMatrix[10] * mMatrix[13] +
            mMatrix[8] * mMatrix[5] * mMatrix[14] -
            mMatrix[8] * mMatrix[6] * mMatrix[13] -
            mMatrix[12] * mMatrix[5] * mMatrix[10] +
            mMatrix[12] * mMatrix[6] * mMatrix[9];

  inv[1] = -mMatrix[1] * mMatrix[10] * mMatrix[15] +
           mMatrix[1] * mMatrix[11] * mMatrix[14] +
           mMatrix[9] * mMatrix[2] * mMatrix[15] -
           mMatrix[9] * mMatrix[3] * mMatrix[14] -
           mMatrix[13] * mMatrix[2] * mMatrix[11] +
           mMatrix[13] * mMatrix[3] * mMatrix[10];

  inv[5] = mMatrix[0] * mMatrix[10] * mMatrix[15] -
           mMatrix[0] * mMatrix[11] * mMatrix[14] -
           mMatrix[8] * mMatrix[2] * mMatrix[15] +
           mMatrix[8] * mMatrix[3] * mMatrix[14] +
           mMatrix[12] * mMatrix[2] * mMatrix[11] -
           mMatrix[12] * mMatrix[3] * mMatrix[10];

  inv[9] = -mMatrix[0] * mMatrix[9] * mMatrix[15] +
           mMatrix[0] * mMatrix[11] * mMatrix[13] +
           mMatrix[8] * mMatrix[1] * mMatrix[15] -
           mMatrix[8] * mMatrix[3] * mMatrix[13] -
           mMatrix[12] * mMatrix[1] * mMatrix[11] +
           mMatrix[12] * mMatrix[3] * mMatrix[9];

  inv[13] = mMatrix[0] * mMatrix[9] * mMatrix[14] -
            mMatrix[0] * mMatrix[10] * mMatrix[13] -
            mMatrix[8] * mMatrix[1] * mMatrix[14] +
            mMatrix[8] * mMatrix[2] * mMatrix[13] +
            mMatrix[12] * mMatrix[1] * mMatrix[10] -
            mMatrix[12] * mMatrix[2] * mMatrix[9];

  inv[2] = mMatrix[1] * mMatrix[6] * mMatrix[15] -
           mMatrix[1] * mMatrix[7] * mMatrix[14] -
           mMatrix[5] * mMatrix[2] * mMatrix[15] +
           mMatrix[5] * mMatrix[3] * mMatrix[14] +
           mMatrix[13] * mMatrix[2] * mMatrix[7] -
           mMatrix[13] * mMatrix[3] * mMatrix[6];

  inv[6] = -mMatrix[0] * mMatrix[6] * mMatrix[15] +
           mMatrix[0] * mMatrix[7] * mMatrix[14] +
           mMatrix[4] * mMatrix[2] * mMatrix[15] -
           mMatrix[4] * mMatrix[3] * mMatrix[14] -
           mMatrix[12] * mMatrix[2] * mMatrix[7] +
           mMatrix[12] * mMatrix[3] * mMatrix[6];

  inv[10] = mMatrix[0] * mMatrix[5] * mMatrix[15] -
            mMatrix[0] * mMatrix[7] * mMatrix[13] -
            mMatrix[4] * mMatrix[1] * mMatrix[15] +
            mMatrix[4] * mMatrix[3] * mMatrix[13] +
            mMatrix[12] * mMatrix[1] * mMatrix[7] -
            mMatrix[12] * mMatrix[3] * mMatrix[5];

  inv[14] = -mMatrix[0] * mMatrix[5] * mMatrix[14] +
            mMatrix[0] * mMatrix[6] * mMatrix[13] +
            mMatrix[4] * mMatrix[1] * mMatrix[14] -
            mMatrix[4] * mMatrix[2] * mMatrix[13] -
            mMatrix[12] * mMatrix[1] * mMatrix[6] +
            mMatrix[12] * mMatrix[2] * mMatrix[5];

  inv[3] = -mMatrix[1] * mMatrix[6] * mMatrix[11] +
           mMatrix[1] * mMatrix[7] * mMatrix[10] +
           mMatrix[5] * mMatrix[2] * mMatrix[11] -
           mMatrix[5] * mMatrix[3] * mMatrix[10] -
           mMatrix[9] * mMatrix[2] * mMatrix[7] +
           mMatrix[9] * mMatrix[3] * mMatrix[6];

  inv[7] = mMatrix[0] * mMatrix[6] * mMatrix[11] -
           mMatrix[0] * mMatrix[7] * mMatrix[10] -
           mMatrix[4] * mMatrix[2] * mMatrix[11] +
           mMatrix[4] * mMatrix[3] * mMatrix[10] +
           mMatrix[8] * mMatrix[2] * mMatrix[7] -
           mMatrix[8] * mMatrix[3] * mMatrix[6];

  inv[11] = -mMatrix[0] * mMatrix[5] * mMatrix[11] +
            mMatrix[0] * mMatrix[7] * mMatrix[9] +
            mMatrix[4] * mMatrix[1] * mMatrix[11] -
            mMatrix[4] * mMatrix[3] * mMatrix[9] -
            mMatrix[8] * mMatrix[1] * mMatrix[7] +
            mMatrix[8] * mMatrix[3] * mMatrix[5];

  inv[15] = mMatrix[0] * mMatrix[5] * mMatrix[10] -
            mMatrix[0] * mMatrix[6] * mMatrix[9] -
            mMatrix[4] * mMatrix[1] * mMatrix[10] +
            mMatrix[4] * mMatrix[2] * mMatrix[9] +
            mMatrix[8] * mMatrix[1] * mMatrix[6] -
            mMatrix[8] * mMatrix[2] * mMatrix[5];

  det = mMatrix[0] * inv[0] + mMatrix[1] * inv[4] + mMatrix[2] * inv[8] +
        mMatrix[3] * inv[12];

  if (det == 0) return false;

  det = 1.0 / det;

  for (i = 0; i < 16; i++) mMatrix[i] = inv[i] * det;

  return true;
}
