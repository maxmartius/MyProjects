#pragma once

#include <math.h>

#include <cstddef>

#include "GLPoint.hpp"
#include "GLVector.hpp"

class GLMatrix {
 public:
  GLMatrix();
  
  GLMatrix(double p00, double p10, double p20, double p30, double p01, double p11, double p21, double p31, double p02, double p12, double p22, double p32, double p03, double p13, double p23, double p33);

  void setColumn(int i, const GLPoint &rhs);

  void setColumn(int i, const GLVector &rhs);

  void setValue(const int row, const int column, const float value);

  GLVector getColumn(int i) const;

  bool inverse();

  double operator()(int row, int column) const;

 private:
  double mMatrix[16];
};

/**
** Gibt die Einträge der Matrix auf drei Nachkommastellen gerundet aus
**/
inline std::ostream &operator<<(std::ostream &os, const GLMatrix &m) {
  for (size_t i = 0; i < 4; ++i) {
    for (size_t j = 0; j < 4; ++j) {
      os << round(m(i, j) * 1000.0f) / 1000.0f << " ";
    }
    os << "\n";
  }
  return os;
}
