package com.smoothstack.lms.common.model;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "t_library_branch")
@Access(AccessType.FIELD)
public class Branch implements Serializable {

        @Id
        @Column(name = "branchId")
        @SequenceGenerator(name = "branch", sequenceName = "branchId", initialValue = 1, allocationSize = 1)
        @GeneratedValue(generator = "branch")
    private long branchId;

        @Column(name = "branchName")
        @NotNull
        @NotBlank
    private String branchName;

        @Column(name = "branchAddress")
        @NotNull
        @NotBlank
    private String branchAddress;

    public Branch() {
    }

    public Branch(@NotNull @NotBlank String branchName, @NotNull @NotBlank String branchAddress) {
        this.branchName = branchName;
        this.branchAddress = branchAddress;
    }

    public long getBranchId() {
        return branchId;
    }

    public void setBranchId(long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch that = (Branch) o;
        return getBranchId() == that.getBranchId() &&
                getBranchName().equals(that.getBranchName()) &&
                getBranchAddress().equals(that.getBranchAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBranchId(), getBranchName(), getBranchAddress());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Branch.class.getSimpleName() + "[", "]")
                .add("id=" + branchId)
                .add("name='" + branchName + "'")
                .add("address='" + branchAddress + "'")
                .toString();
    }
}