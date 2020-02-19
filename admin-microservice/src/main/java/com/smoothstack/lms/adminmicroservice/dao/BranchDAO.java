package com.smoothstack.lms.adminmicroservice.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.smoothstack.lms.adminmicroservice.model.Branch;

@Component
public class BranchDAO extends BaseDAO<Branch> {

	public BranchDAO() throws ClassNotFoundException, SQLException {
		super();
	}

	public Integer addBranch(Branch branch) throws ClassNotFoundException, SQLException {
		return save("insert into tbl_library_branch (branchName,branchAddress) values (?,?)",
				new Object[] { branch.getBranchName(), branch.getBranchAddress() });
	}

	public void updateBranch(Branch branch) throws SQLException, ClassNotFoundException {
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId() });
	}

	public void deleteBranch(Branch branch) throws ClassNotFoundException, SQLException {
		save("delete from tbl_library_branch where branchID = ?", new Object[] { branch.getBranchId() });
	}

	public void deleteBranchCopies(Branch branch) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book_copies where branchID = ?", new Object[] { branch.getBranchId() });
	}

	public List<Branch> readBranch() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_library_branch", null);
	}
	
	public Branch readBranchById(Integer branchId) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_library_branch where branchId = ?", new Object[] {branchId}).get(0);
	}

	@Override
	List<Branch> extractData(ResultSet rs) throws SQLException {
		List<Branch> branches = new ArrayList<Branch>();
		while (rs.next()) {
			Branch br = new Branch();
			br.setBranchId(rs.getInt("branchId"));
			br.setBranchName(rs.getString("branchName"));
			br.setBranchAddress(rs.getString("branchAddress"));
			branches.add(br);
		}
		return branches;
	}

	@Override
	List<Branch> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		return extractData(rs);
	}

}
